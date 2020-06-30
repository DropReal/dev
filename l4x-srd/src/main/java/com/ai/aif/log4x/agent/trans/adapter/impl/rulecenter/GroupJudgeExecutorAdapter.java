package com.ai.aif.log4x.agent.trans.adapter.impl.rulecenter;


import com.ai.aif.log4x.agent.deps.javassist.CtMethod;
import com.ai.aif.log4x.agent.trans.adapter.impl.AbsJavassistSwitchAdapter;
import com.ai.aif.log4x.agent.util.WrappedStringBuilder;


public class GroupJudgeExecutorAdapter extends AbsJavassistSwitchAdapter {
    @Override
    public String addAfterInvoke(CtMethod arg0, String arg1, String arg2) {
        // TODO Auto-generated method stub
        WrappedStringBuilder buf = new WrappedStringBuilder();
        buf.appendln("com.ai.aif.log4x.message.format.Trace trace = com.ai.aif.log4x.Log4xManager.client().getCurrentTrace();");
        buf.appendln("trace.addData(\"isSuccess\",java.lang.String.valueOf("+arg2+".isSuccess()));");
        buf.appendln("com.ai.aif.log4x.Log4xManager.client().finishTrace(true);");
        return buf.toString();
    }

    @Override
    public String addBeforeInvoke(CtMethod ctMethod) {
        // TODO Auto-generated method stub
        WrappedStringBuilder buf = new WrappedStringBuilder();
        buf.appendln("com.ai.rule.engine.fact.interfaces.IRequestFact fact_ = $2.getRequestFact();");
        buf.appendln("java.lang.String traceContext = fact_.getTraceContext();");
        buf.appendln("if(traceContext != null && traceContext.length() > 0){com.ai.aif.log4x.Log4xManager.client().resetTraceContext(traceContext);}");
        buf.appendln("com.ai.aif.log4x.message.format.Trace trace = com.ai.aif.log4x.Log4xManager.client().getTrace();");
        buf.appendln("com.ai.rule.engine.rule.define.interfaces.AbstractRule rule_ = $1;");
        buf.appendln("trace.setServiceName($0.getClass().getName() + \"." + ctMethod.getName() + "\");");
        buf.appendln("trace.setCallType(\"RULE\");");
        buf.appendln("trace.addData(\"ruleId\",rule_.getRuleDefine().getRuleId());");
        buf.appendln("trace.addData(\"sceneId\",rule_.getSceneId());");
        buf.appendln("trace.addData(\"ruleType\",rule_.getRuleDefine().getRuleType());");
        buf.appendln("trace.addData(\"ruleCode\",rule_.getRuleDefine().getRuleCode());");
        buf.appendln("trace.addData(\"ruleContent\",rule_.getRuleDefine().getContent());");
        buf.appendln("trace.addData(\"tenantCode\",fact_.getTenantCode());");
        buf.appendln("trace.addData(\"baseCode\",fact_.getBaseCodeList().toString());");
        buf.appendln("trace.addData(\"accessNum\",fact_.getAccessNum());");
        buf.appendln("trace.addData(\"busiCode\",fact_.getBusiCode());");
        buf.appendln("trace.addData(\"staffId\",fact_.getStaffId());");
        buf.appendln("trace.addData(\"busiTraceId\",fact_.getTraceId());");
        buf.appendln("com.ai.aif.log4x.Log4xManager.client().startTrace(trace);");
        return buf.toString();
    }

    @Override
    public String addInExceptionCatch(CtMethod method, String exName, String vName) {
        // TODO Auto-generated method stub
        WrappedStringBuilder buf = new WrappedStringBuilder();
        buf.appendln("trace.setThrowable(" + vName + ");");
        buf.appendln("com.ai.aif.log4x.Log4xManager.client().finishTrace(false);");
        return buf.toString();
    }

    @Override
    public String[] getInjectedMethodHeaders() {
        // TODO Auto-generated method stub
        return new String[] { "protected com.ai.rule.engine.result.interfaces.IRuleMsg judge(com.ai.rule.engine.rule.define.interfaces.AbstractRule rule,com.ai.rule.engine.fact.handler.interfaces.IFactHandler factHandler)" };
    }
}
