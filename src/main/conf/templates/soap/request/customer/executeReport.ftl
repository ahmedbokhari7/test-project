<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ext="http://external.interfaces.ers.seamless.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ext:executeReport>
         <!--Optional:-->
         <context>
            <!--Optional:-->
            <channel>?</channel>
            <!--Optional:-->
            <clientComment>?</clientComment>
            <!--Optional:-->
            <clientId>?</clientId>
            <!--Optional:-->
            <prepareOnly>?</prepareOnly>
            <!--Optional:-->
            <clientReference>?</clientReference>
            <clientRequestTimeout>${clientRequestTimeout}</clientRequestTimeout>
            <!--Optional:-->
            <initiatorPrincipalId>
               <!--Optional:-->
               <id>${initiatorPrincipalId}</id>
               <!--Optional:-->
               <type>${initiatorPrincipalType}</type>
               <!--Optional:-->
               <userId>${initiatorPrincipalUserId}</userId>
            </initiatorPrincipalId>
            <!--Optional:-->
            <password>${password}</password>
            <!--Optional:-->
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
         <!--Optional:-->
         <reportId>${reportId}</reportId>
         <!--Optional:-->
         <language>${language}</language>
         <!--Optional:-->
         <parameters>
            <!--Optional:-->
            <parameter>
               <!--Zero or more repetitions:-->
               <entry>
                  <!--Optional:-->
                  <key>?</key>
                  <!--Optional:-->
                  <value>?</value>
               </entry>
            </parameter>
         </parameters>
      </ext:executeReport>
   </soapenv:Body>
</soapenv:Envelope>