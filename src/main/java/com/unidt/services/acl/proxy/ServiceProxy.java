package com.unidt.services.acl.proxy;

import com.unidt.services.acl.proxy.priv.CRMProxy;

public class ServiceProxy {
    public static CRMProxy createCRMProxy() {
        return CRMProxy.create();
    }
}
