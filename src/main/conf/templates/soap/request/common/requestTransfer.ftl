<#list product.getProductBySenderReceiverAccountTypeId(reseller.getReseller(1,0).getAccountTypeId(),reseller.getReseller(1,0).getChildReseller(0).getAccountTypeId()) as prod>
<soapJobRequestTransfer>
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                      xmlns:ext="http://external.interfaces.ers.seamless.com/">
        <soapenv:Header/>
        <soapenv:Body>
            <ext:requestTransfer>
                <context>
                    <channel>WSClient</channel>
                    <clientComment>?</clientComment>
                    <clientId>ERS</clientId>
                    <prepareOnly>false</prepareOnly>
                    <clientReference>?</clientReference>
                    <clientRequestTimeout>500</clientRequestTimeout>
                    <initiatorPrincipalId>
                        <id>${reseller.getReseller(2,0).getResellerId()}</id>
                        <type>RESELLERUSER</type>
                        <userId>${reseller.getReseller(2,0).getPosUserId()}</userId>
                    </initiatorPrincipalId>
                    <password>${reseller.getReseller(2,0).getPosPassword()}</password>
                    <transactionProperties>
                        <entry>
                            <key>?</key>
                            <value>?</value>
                        </entry>
                    </transactionProperties>
                </context>
                <senderPrincipalId>
                    <id>${reseller.getReseller(2,0).getResellerId()}</id>
                    <type>RESELLERUSER</type>
                    <userId>${reseller.getReseller(2,0).getPosUserId()}</userId>
                </senderPrincipalId>
                <receiverPrincipalId>
                    <id>${reseller.getReseller(2,0).getChildReseller(0).getResellerId()}</id>
                    <type>RESELLERUSER</type>
                    <userId>${reseller.getReseller(2,0).getChildReseller(0).getPosUserId()}</userId>
                </receiverPrincipalId>
                <senderAccountSpecifier>
                    <accountId>${reseller.getReseller(2,0).getAccountId()}</accountId>
                    <accountTypeId>${reseller.getReseller(2,0).getAccountTypeId()}</accountTypeId>
                </senderAccountSpecifier>
                <receiverAccountSpecifier>
                    <accountId>${reseller.getReseller(2,0).getChildReseller(0).getAccountId()}</accountId>
                    <accountTypeId>${reseller.getReseller(2,0).getChildReseller(0).getAccountTypeId()}</accountTypeId>
                </receiverAccountSpecifier>
                <productId>${prod.getProductGlobalSKU()}</productId>
                <amount>
                    <currency>${reseller.getReseller(2,0).getCurrency()}</currency>
                    <value>1000</value>
                </amount>
            </ext:requestTransfer>
        </soapenv:Body>
    </soapenv:Envelope>
</soapJobRequestTransfer>
</#list>