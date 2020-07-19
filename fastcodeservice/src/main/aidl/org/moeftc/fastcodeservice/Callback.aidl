// CallbackHandlerImpl.aidl
package com.dex.service;

// Declare any non-default types here with import statements

interface Callback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void loadOpModes(String opModes, in byte[] dexFile);
}
