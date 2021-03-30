<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ext="http://external.interfaces.ers.seamless.com/">
<soapenv:Header/>
<soapenv:Body>
<ext:cancelPurchase>
<context>
<initiatorPrincipalId>
<id>${initiatorPrincipalId}</id>
<type>${initiatorPrincipalType}</type>
<userId>${initiatorPrincipalUserId}</userId>
</initiatorPrincipalId>
<password>${password}</password>
</context>
<cancelPurchaseOrder>
<!--Zero or more repetitions:-->
<productSpecifier>
<productId>${productId}</productId>
<productIdType>${productIdType}</productIdType>
</productSpecifier>
<purchaseCount>${purchaseCount}</purchaseCount>
<!--Zero or more repetitions:-->
<productDetails>
<!--Optional:-->
<map>
<!--Zero or more repetitions:-->
<entry>
<!--Optional:-->
<key>${key}</key>
<!--Optional:-->
<value>${serialValue}</value>
</entry>
</map>
</productDetails>
</cancelPurchaseOrder>
</ext:cancelPurchase>
</soapenv:Body>
</soapenv:Envelope>