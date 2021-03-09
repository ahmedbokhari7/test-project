package com.ahmed.enums;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;

public enum Customer {
    AF_MTN("af-mtn"),
    BF_TELECEL("bf-telecel"),
    BITREFILL("bitRefill"),
    BJ_GLO("bj-glo"),
    BJ_MTN("bj-mtn"),
    BP("BP"),
    BR_DT("br-dt"),
    BS_NEWCO("bs-newco"),
    CD_LIN("cd-lin"),
    CG_MTN("cg-mtn"),
    CI_MTN("ci-mtn"),
    CMS_STD("cms-std"),
    DJ_EVT("dj-evt"),
    ERS_STD("ers-std"),
    ET_SDT("et-sdt"),
    FI_DNA("fi-dna"),
    FR_EVODIAL("fr-evodial"),
    GB_MTN("gb-mtn"),
    GH_GLO("gh-glo"),
    GH_MTN("gh-mtn"),
    GN_MTN("gn-mtn"),
    IR_MCI("ir-mci"),
    LR_MTN("lr-mtn"),
    LV_LETTEL("lv-lettel"),
    MFS_STD("mfs-std"),
    NG_GLO("ng-glo"),
    NG_MTN("ng-mtn"),
    RW_MTN("rw-mtn"),
    SD_MTN("sd-mtn"),
    SE_BANKSYSTEM("se-banksystem"),
    SE_EVD("se-evd"),
    SE_MRS("se-mrs"),
    SE_VT("se-vt"),
    SN_EXPRESSO("sn-expresso"),
    SS_MTN("ss-mtn"),
    STARLINK("starlink"),
    SWZ_MOBILE("swz-mobile"),
    SY_MTN("sy-mtn"),
    SZ_MTN("sz-mtn"),
    TH_GOSOFT("th-gosoft"),
    YE_MTN("ye-mtn"),
    YE_TMK("ye-tmk"),
    ZA_MTN("za-mtn"),
    ZM_MTN("zm-mtn"),
    ZAIN_SA("zain-sa"),
    ;

    private final String customer;

    public String getCustomer() {
        return customer;
    }

    Customer(String str) {
        customer = str;
    }

    public static String[] getCustomers() {
        List<String> collect = Arrays.stream(values()).map(Customer::getCustomer).collect(Collectors.toList());
        return collect.toArray(new String[]{});
    }

    public static Customer lookup(String customer) {
        for (Customer customer1 : Customer.values()) {
            if (customer1.getCustomer().equalsIgnoreCase(customer))
                return customer1;
        }
        throw new IllegalArgumentException("No enum constant Customer." + customer);
    }
}