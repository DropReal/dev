package com.ai.aif.log4x.agent.trans.adapter.impl.rulecenter;


import com.ai.aif.log4x.agent.deps.javassist.CtMethod;
import com.ai.aif.log4x.agent.trans.adapter.impl.AbsJavassistSwitchAdapter;
import com.ai.aif.log4x.agent.util.WrappedStringBuilder;


public class SceneMatcherAdapter extends AbsJavassistSwitchAdapter {
    @Override
    public String addAfterInvoke(CtMethod arg0, String arg1, String arg2) {
        // TODO Auto-generated method stub
        WrappedStringBuilder buf = new WrappedStringBuilder();
        buf.appendln("if(featureJson.containsKey(\"traceId\")) {");
        buf.appendln("com.ai.aif.log4x.message.format.Trace trace = com.ai.aif.log4x.Log4xManager.client().getCurrentTrace();");
        buf.appendln("com.alibaba.fastjson.JSONArray json = (com.alibaba.fastjson.JSONArray) com.alibaba.fastjson.JSON.toJSON("+arg2+");");
        buf.appendln("trace.setRespBody(json==null?\"\":json.toJSONString());");
        buf.appendln("com.ai.aif.log4x.Log4xManager.client().finishTrace(true);");
        buf.appendln("}");
        return buf.toString();
    }

    @Override
    public String addBeforeInvoke(CtMethod ctMethod) {
        // TODO Auto-generated method stub
        WrappedStringBuilder buf = new WrappedStringBuilder();
        buf.appendln("com.alibaba.fastjson.JSONObject tenantJson = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSON.toJSON($1);");
        buf.appendln("com.alibaba.fastjson.JSONObject featureJson = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSON.toJSON($2);");
        buf.appendln("if(featureJson.containsKey(\"traceId\")) {");
        buf.appendln("com.ai.aif.log4x.message.format.Trace trace = com.ai.aif.log4x.Log4xManager.client().getTrace();");
        buf.appendln("trace.setServiceName($class.getName() + \"." + ctMethod.getName() + "\");");
        buf.appendln("trace.setCallType(\"MATCHER\");");
        buf.appendln("com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();");

        buf.appendln("json.put(\"tenantParam\",tenantJson);");
        buf.appendln("json.put(\"featureParam\",featureJson);");
        buf.appendln("trace.setReqBody(json.toJSONString());");
        buf.appendln("java.lang.String tenantCode_ = tenantJson.getString(\"tenantCode\");");
        buf.appendln("java.lang.String baseCode_ = tenantJson.getString(\"baseCodes\");");
        buf.appendln("java.lang.String traceId_ = featureJson.getString(\"traceId\");");
        buf.appendln("java.lang.String accessNum_ = featureJson.getString(\"busi_num\");");
        buf.appendln("java.lang.String busiCode_ = featureJson.getString(\"busi_code_code\");");
        buf.appendln("java.lang.String staffId_ = featureJson.getString(\"staffId\");");
        buf.appendln("trace.addData(\"tenantCode\",tenantCode_);");
        buf.appendln("trace.addData(\"baseCode\",baseCode_);");
        buf.appendln("trace.addData(\"accessNum\",accessNum_);");
        buf.appendln("trace.addData(\"busiCode\",busiCode_);");
        buf.appendln("trace.addData(\"staffId\",staffId_);");
        buf.appendln("trace.addData(\"busiTraceId\",traceId_);");
        buf.appendln("trace.addData(\"feature\",featureJson.toJSONString());");
//        buf.appendln("java.lang.String intactTraceId = featureJson.getString(\"intactTraceId\");");
//        buf.appendln("trace.setTraceId(intactTraceId);");
        buf.appendln("com.ai.aif.log4x.Log4xManager.client().startTrace(trace);");
        buf.appendln("}");
        return buf.toString();
    }

    @Override
    public String addInExceptionCatch(CtMethod method, String exName, String vName) {
        // TODO Auto-generated method stub
        WrappedStringBuilder buf = new WrappedStringBuilder();
        buf.appendln("if(featureJson.containsKey(\"traceId\")) {");
        buf.appendln("com.ai.aif.log4x.message.format.Trace trace = com.ai.aif.log4x.Log4xManager.client().getCurrentTrace();");
        buf.appendln("trace.setThrowable(" + vName + ");");
        buf.appendln("com.ai.aif.log4x.Log4xManager.client().finishTrace(false);");
        buf.appendln("}");
        return buf.toString();
    }

    @Override
    public String[] getInjectedMethodHeaders() {
        // TODO Auto-generated method stub
        return new String[] {
                "public static java.util.List/*<com.ai.rule.engine.scene.interfaces.IScene>*/ match(com.ai.rule.common.data.interfaces.IFastMap tenantParam,com.ai.rule.common.data.interfaces.IFastMap featureMap,boolean isTestStaffId)"};
    }
}
