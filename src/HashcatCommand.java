import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HashcatCommand {
    public static void magic(Hash hashObject) {
        for (String mode : hashObject.modesToAttempt) {
            String command = String.format("hashcat --force -m %s %s /home/daisy/parrot/rockyou.txt", mode, hashObject.hash);
            System.out.println(command);
            try {
                Process proc = Runtime.getRuntime().exec(command);
                proc.waitFor();
                proc = Runtime.getRuntime().exec(String.format("bash /home/daisy/hashcat-GUI/check_potfile.sh %s", hashObject.hash));
                Scanner sc = new Scanner(proc.getInputStream());
                if (sc.hasNext()) {
                    hashObject.password = sc.next();
                    hashObject.verifiedHashType = HashTypeIdentifier.getTypeFromMode(mode);
                    return;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void hailMary(Hash hash) throws IOException {
        System.out.println("HAIL MARY!!!");
        hash.modesToAttempt = HashTypeIdentifier.getAllModes();
        magic(hash);
    }
}
