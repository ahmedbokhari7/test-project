package com.ahmed.enums;


public enum Product {
    ERS("ers", "Standard Automation Tags"),
    MFS("mfs", "Standard Automation Tags"),
    SFO("sfo", "SFO Backend Tags"),
    VMS("vms", "VMS"),
    EVD("evd", "EVD"),
    CMS("cms", "CMS");

    private final String productName;
    private final String jiraTag;

    Product(String productName, String jiraTag) {
        this.productName = productName;
        this.jiraTag = jiraTag;
    }

    public String getProductName() {
        return productName;
    }

    public String getJiraTag() {
        return jiraTag;
    }

    public static Product lookup(String productname) {
        for (Product product1 : Product.values()) {
            if (product1.getProductName().equalsIgnoreCase(productname))
                return product1;
        }
        throw new IllegalArgumentException("No enum constant Product." + productname);
    }

}