<h1>Interprocess Communication Tests</h1>

Welcome to my answer for a Bently Nevada take home challenge.

I was asked to answer questions about testing, and I chose to implement them in java.

<h3>How it proves that rack/monitoring is working correctly.</h3>

It tests the bounds of the input for the API which for this monitoring system exist above 4000, at 4000, and below it.

<h3>How would you implement the API for ReadRelayState()?</h3>

The process itself launches two threads, one for the monitor system, and one for the relay. Within the process exists a volatile variable called "frequency".
A volatile variable exists within RAM and is perfect for this system as the frequency is supposed to be changed by a controller of some kind. This is exactly
what the volatile keyword can be used for.

The API for this process is a while loop that waits for text from another process. If it reads a "ReadRelayState" from another process it will return the state of the relay which is 
"true" for close and "false" for open (they are a series of bytes to be read in).

"SetFrequency 4000" can also be used to simulate the changing of the frequency through the API. You can change 4000 to any number. The default SetPoint is 4000 for this monitor system.

"Shutdown" will end the process.