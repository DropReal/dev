package com.ai.aif.log4x.agent.trans.adapter.impl.rulecenter;


import com.ai.aif.log4x.agent.deps.javassist.CtMethod;
import com.ai.aif.log4x.agent.trans.adapter.impl.AbsJavassistSwitchAdapter;
import com.ai.aif.log4x.agent.util.WrappedStringBuilder;


public class RuleCheckAbstractServiceAdapter extends AbsJavassistSwitchAdapter {
    @Override
    public String addAfterInvoke(CtMethod arg0, String arg1, String arg2) {
        // TODO Auto-generated method stub
        WrappedStringBuilder buf = new WrappedStringBuilder();
        buf.appendln("com.ai.aif.log4x.message.format.Trace trace = com.ai.aif.log4x.Log4xManager.client().getCurrentTrace();");
        buf.appendln("trace.setRespBody("+arg2+");");
        buf.appendln("com.ai.aif.log4x.Log4xManager.client().finishTrace(true);");
        return buf.toString();
    }

    @Override
    public String addBeforeInvoke(CtMethod ctMethod) {
        // TODO Auto-generated method stub
        WrappedStringBuilder buf = new WrappedStringBuilder();

        buf.appendln("com.ai.aif.log4x.message.format.Trace trace = com.ai.aif.log4x.Log4xManager.client().getTrace();");

        buf.appendln("trace.setServiceName($0.getClass().getName() + \"." + ctMethod.getName() + "\");");
        buf.appendln("trace.setCallType(\"RULE_CHECK_SERVICE\");");
        buf.appendln("com.alibaba.fastjson.JSONObject reqJson = com.alibaba.fastjson.JSONObject.parseObject($1);");
        buf.appendln("com.alibaba.fastjson.JSONObject body = reqJson.getJSONObject(\"body\");");
        buf.appendln("com.alibaba.fastjson.JSONObject head = reqJson.getJSONObject(\"header\");");
        buf.appendln("com.alibaba.fastjson.JSONObject publicJson = body.getJSONObject(\"publicParam\");");
        buf.appendln("com.alibaba.fastjson.JSONObject tenantJson = body.getJSONObject(\"tenantParam\");");
        buf.appendln("java.lang.String tenantCode_ = tenantJson.getString(\"tenantCode\");");
        buf.appendln("java.lang.String baseCode_ = tenantJson.getString(\"baseCodes\");");
        buf.appendln("java.lang.String accessNum_ = publicJson.getString(\"accessNum\");");
        buf.appendln("java.lang.String busiCode_ = publicJson.getString(\"busiCode\");");
        buf.appendln("java.lang.String staffId_ = publicJson.getString(\"staffId\");");
        buf.appendln("java.lang.String traceId_ = publicJson.getString(\"traceId\");");
        buf.appendln("java.lang.String district_ = publicJson.getString(\"district\");");
        buf.appendln("java.lang.String channelId_ = publicJson.getString(\"channelId\");");
        buf.appendln("trace.addData(\"tenantCode\",tenantCode_);");
        buf.appendln("trace.addData(\"baseCode\",baseCode_);");
        buf.appendln("trace.addData(\"accessNum\",accessNum_);");
        buf.appendln("trace.addData(\"busiCode\",busiCode_);");
        buf.appendln("trace.addData(\"staffId\",staffId_);");
        buf.appendln("trace.addData(\"busiTraceId\",traceId_);");
        buf.appendln("trace.addData(\"district\",district_);");
        buf.appendln("trace.setChannel(channelId_);");
        buf.appendln("trace.setReqBody($1);");
//        buf.appendln("java.lang.String intactTraceId = head.getString(\"TRADE_INTACT_ID\");");
//        buf.appendln("trace.setTraceId(intactTraceId);");
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
        return new String[] { "public java.lang.String service(java.lang.String request)" };
    }
}
