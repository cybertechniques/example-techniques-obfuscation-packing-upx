package disable_upx_d;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class DisableUpxD
{

    public static void main(String[] args)
    {
        Path p = FileSystems.getDefault().getPath("", "hello_world_packed.exe");
        System.out.println("PATH: " + p.toAbsolutePath().toString());
        
        try
        {
            byte [] fileData = Files.readAllBytes(p);
            
            boolean patched = false;
            
            System.out.println("file size: " + fileData.length);
            
            for(int i=0; i < fileData.length; ++i)
            {
                System.out.println(Integer.toHexString(fileData[i]));
                if((fileData[i] == 0x55) && (fileData[i+1] == 0x50) && (fileData[i+2] == 0x58) && (fileData[i+3] == 0x30))
                {
                    fileData[i] = 0x41; // Change U 0x55 to A 0x41
                    
                    break;
                }
            }
            
            if(patched)
            {
                FileOutputStream fos = new FileOutputStream("hello_world_packed_modified.exe");
                fos.write(fileData);
                fos.close();
                
                System.out.println("Your file is patched now");
            }
            else
            {
                System.out.println("Your file was not patched");
            }
            
            
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
