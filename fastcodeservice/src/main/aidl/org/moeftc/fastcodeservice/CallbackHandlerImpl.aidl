// CallbackHandlerImpl.aidl
package org.moeftc.fastcodeservice;

// Declare any non-default types here with import statements
import org.moeftc.fastcodeservice.Callback;
interface CallbackHandlerImpl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void register(Callback callback);
//    void respond(int code);
}
