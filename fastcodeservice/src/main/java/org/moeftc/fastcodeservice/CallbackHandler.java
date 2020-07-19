package com.dex.service

object CallbackHandler : CallbackHandlerImpl.Stub() {
    val callbacks = mutableListOf<Callback>()
    override fun register(callback: Callback) {
        callbacks.add(callback)
    }

//    override fun respond(code: Int) {
////        Log.e("writing", "data$code")
//        lastSocket.getOutputStream().write(code)
//        lastSocket.getOutputStream().close()
//    }

    @JvmStatic
    fun broadcast(opModes: String, dexFile: ByteArray) {
        callbacks.forEach { it.loadOpModes(opModes, dexFile) }
    }

}

