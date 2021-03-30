<customer name="${featureData.getCustomerName()}">
    <#list featureData.getFeatureMap()?keys as featureName>
    <feature name="${featureName}">
        <#list featureData.getFeatureMap()[featureName] as testStatus>
        <tag name="${testStatus.getTagName()}" testCase="${testStatus.getTestCaseName()}" result="${testStatus.getResult()}" status="${testStatus.getStatus()}"/>
        </#list>
    </feature>
    </#list>
</customer>

