# kotlin-pcsc

work-in-progress bindings for the PC/SC API in Kotlin.

This takes some small liberties with the PC/SC API to make it object oriented.

## Implemented:

Currently only tested on macOS with Kotlin/Native.

* SCardEstablishContext -> Context.establish
* SCardIsValidContext -> Context.isValid
* SCardReleaseContext -> Context.release
* SCardListReaders -> Context.listReaders
* SCardConnect -> Context.connect
* SCardDisconnect -> Card.disconnect
* SCardReconnect -> Card.reconnect

## TODO:

### Transactions and transmission

* SCardBeginTransaction
* SCardEndTransaction
* SCardCancel
* SCardTransmit

### Status inquiry

* SCardStatus
* SCardGetStatusChange

### Control and attributes

* SCardControl
* SCardGetAttrib
* SCardSetAttrib

### Informational

* SCardListReaderGroups

