<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ext="http://external.interfaces.ers.seamless.com/">
<soapenv:Header/>
<soapenv:Body>
<ext:requestPurchase>
<context>
<channel>WSClient</channel>
<clientComment>?</clientComment>
<clientId>ERS</clientId>
<prepareOnly>false</prepareOnly>
<clientReference>?</clientReference>
<clientRequestTimeout>${clientRequestTimeout}</clientRequestTimeout>
<initiatorPrincipalId>
<id>${initiatorPrincipalId}</id>
<type>${initiatorPrincipalType}</type>
<userId>${initiatorPrincipalUserId}</userId>
</initiatorPrincipalId>
<password>${password}</password>
<transactionProperties>
<!--Zero or more repetitions:-->
<entry>
<!--Optional:-->
<key>?</key>
<!--Optional:-->
<value>?</value>
</entry>
</transactionProperties>
</context>
<senderPrincipalId>
<id>${senderPrincipalId}</id>
<type>${senderPrincipalType}</type>
<userId>${senderPrincipalUserId}</userId>
</senderPrincipalId>
<receiverPrincipalId>
<id>${receiverPrincipalId}</id>
<type>${receiverPrincipalType}</type>
<userId>?</userId>
</receiverPrincipalId>
<senderAccountSpecifier>
<accountId>${senderAccountId}</accountId>
<accountTypeId>${senderAccountTypeId}</accountTypeId>
</senderAccountSpecifier>
<receiverAccountSpecifier>
<accountId>${receiverAccountId}</accountId>
<accountTypeId>${receiverAccountTypeId}</accountTypeId>
</receiverAccountSpecifier>
<!--Zero or more repetitions:-->
<purchaseOrder>
<!--Zero or more repetitions:-->
<productSpecifier>
<productId>${productId}</productId>
<productIdType>${productIdType}</productIdType>
</productSpecifier>
<purchaseCount>${purchaseCount}</purchaseCount>
</purchaseOrder>
<!--Optional:-->
<extraFields>
<!--Zero or more repetitions:-->
<entry>
<!--Optional:-->
<key>?</key>
<!--Optional:-->
<value>?</value>
</entry>
</extraFields>
</ext:requestPurchase>
</soapenv:Body>
</soapenv:Envelope>