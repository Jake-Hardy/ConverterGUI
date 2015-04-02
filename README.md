# ConverterGUI
A simple Binary &lt;==> Decimal converter I'm working on while I learn how to use Swing.

=====USE======================<br />
<ul>
  <li>
    Enter a binary number (between 0 and 1111 1111 11) or a decimal number (between 1 and 255)
    press Enter, and the originally entered number and the converted number are returned.
  </li>

  <li>
    Binary numbers need to be entered with no spaces, and negative base-10 numbers will return 0.
  </li>
</ul>
<br />
=====Known=Issues=and=Bugs=====<br />
<ul>
  <li>
    Trying to convert decimal numbers that contain only 1s and 0s causes problems. I need to add 
    a way to select whether the user is converting from binary to decimal or vice verse. This 
    will likely be the next thing to get fixed.
  </li>
  
  <li>
    Negative values all return 0.
  </li>
    
  <li>
    Non-numeric Strings entered in the textField will throw a NumberFormatException, but the catch{} block doesn't
    seem to work correctly. 
    <ul>
      <li>
        Binary values over 1111 1111 11 (1023) also throw this Exception.
      </li>
    </ul>
  </li>
</ul>
