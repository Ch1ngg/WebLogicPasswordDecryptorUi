<%@page pageEncoding="utf-8"%>
<%@page import="weblogic.security.internal.*,weblogic.security.internal.encryption.*"%>
<%
   EncryptionService es = null;
   ClearOrEncryptedService ces = null;
    String s = null;
    s="{AES}wfFNcH6k+9Nz22r1gCMnylgUr9Ho5kz8nDgib/TuOZU=";
    es = SerializedSystemIni.getEncryptionService();
    if (es == null) {
       out.println("Unable to initialize encryption service");
        return;
    }
    ces = new ClearOrEncryptedService(es);
    if (s != null) {
        out.println("\nDecrypted Password is:" + ces.decrypt(s));
    }
%>