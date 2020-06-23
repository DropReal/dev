package com.ai.aif.log4x.agent.trans.adapter.impl.rulecenter;


import com.ai.aif.log4x.agent.deps.javassist.CtMethod;
import com.ai.aif.log4x.agent.trans.adapter.impl.AbsJavassistSwitchAdapter;
import com.ai.aif.log4x.agent.util.WrappedStringBuilder;


public class RuleInsParamterAdapter extends AbsJavassistSwitchAdapter {
    @Override
    public String addAfterInvoke(CtMethod arg0, String arg1, String arg2) {
        // TODO Auto-generated method stub
        WrappedStringBuilder buf = new WrappedStringBuilder();
        buf.appendln("com.ai.aif.log4x.message.format.Trace trace = com.ai.aif.log4x.Log4xManager.client().getCurrentTrace();");
        buf.appendln("if(null != "+arg2+"){");
        buf.appendln("trace.addData(\"isSuccess\",java.lang.String.valueOf("+arg2+"));}");
        buf.appendln("com.ai.aif.log4x.Log4xManager.client().finishTrace(true);");
        return buf.toString();
    }

    @Override
    public String addBeforeInvoke(CtMethod ctMethod) {
        // TODO Auto-generated method stub
        WrappedStringBuilder buf = new WrappedStringBuilder();
        buf.appendln("com.ai.aif.log4x.message.format.Trace trace = com.ai.aif.log4x.Log4xManager.client().getTrace();");
        buf.appendln("trace.setServiceName($0.getClass().getName() + \"." + ctMethod.getName() + "\");");
        buf.appendln("trace.setCallType(\"RULEFRAME_GROUP_RUN\");");
        buf.appendln("trace.addData(\"ruleCode\",(java.lang.String) $1);");
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
        return new String[] { "public java.lang.Object get(java.lang.Object key)" };
    }
}
