**Assumptions Made**

In the interest of keeping this exercise simple and concise I have assumed the all input code sequences are legal, so it would be up to the 
client to parse instructions and check for legality. Having said that there is a facility to stop execution of all tasks should a task fail 
to complete:

PpsProcessor.forceStop()

**Test GUI**

I have created a very simple GUI for testing adhoc code sequences and also for proof of concept that my implementation of PpsProcessor could 
be used successfully.

*Instruction*

1. Enter legal instructions in the top text area.
2. Press submit button.

The results will be displayed in the bottom text area. If the task fails to complete then tasks can be stopped by pressing the stop execution 
button.

The main method for this can be found in:

com.codecrocodile.gui.Start


**Design Decisions**

1. To enable easy addition of new instructions, instructions execution is encapsulated in its own class of type com.codecrocodile.instruction.
Instruction and a factory is used extract instantiation away from the main code.


**Other Notes**

Code commenting is minimal if any can be found at all. Again this is to keep the code simple and concise. The code should be simple and self 
commenting in this case.

 