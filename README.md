# UPX Example


### How to use upx to pack an executable:
* Download the UPX program from: upx.sourceforge.net

* Use UPX to compress executable
  * I made a simple [Hello World program](hello_world.cpp) in c++
    <img src=images/hello_world.png height=150 />
  * Linux
    * Using the command g++ -o hello_world hello_world.cpp
      * I originally tried to pack the executable which was 9 kilobytes in size but got a NotCompressibleException. This was the result of the binary being too small for UPX to compress. In order to make it bigger to try and compress I compiled the hello_world program statically
        <img src=images/upx_not_compressible_error.png height=150 />
    * Using: g++ -static -o hello_world hello_world.cpp
      * The resulting size was 1.6 megabytes
        * Once packed using the UPX packer it reduced the size 32.43%
  * Windows on linux
    * Using mingw
      * i686-w64-mingw32-g++ -static -static-libgcc -static-libstdc++ -o hello_world.exe hello_world.cpp provides us with a windows binary
  * Using upx you can pack the binary with:
    * upx -o hello_world_packed.exe hello_world.exe
  * The problem is that we can now use the upx -d flag on an executable to unpack it

* How to unpack a packed upx file
  * It is easy to unpack a upx packed file using the upx -d flag
    <img src=images/upx_unpack.png height=150 />

* How to make a packed upx file unpackable when using the upx -d flag
  * One way is to create a program that will modify the contents of your UPX packed program
    * I first did a manual verification by using CFF Explorer and hex editing at the UPX0 header section
      * You will see hex values x55 x50 x58 x30
      * You can change the first 3 hex values to x41
      * When you try to run upx -d on the executable, you can no longer unpack it
        <img src=images/upx_unpackable.png height=150 />
    * For this example I created a [simple java program](DisableUpxD.java) that reads a upx packed file and changes a single character in the file to prevent the upx -d flag from working
      * It should be noted that this is easily defeatable using multiple techniques but it shows how easy it is to defeat a single unpacker process
  * Another way is to modify the UPX source code to automatically generate packed executables that cant be unpacked using the UPX -d flag
    * Rather than repeating the steps that were outlined in the following pdf I am just going to link it.
      * https://dl.packetstormsecurity.net/papers/general/Using_UPX_as_a_security_packer.pdf
