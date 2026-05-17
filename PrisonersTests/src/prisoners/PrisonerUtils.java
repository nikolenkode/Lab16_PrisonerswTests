package prisoners;

import container.MyLinkedList;
import java.util.regex.Pattern;

public class PrisonerUtils {

    public static boolean isValidFullName(String name) {
        return name != null && name.matches("^[A-ZА-ЯІЇЄ][a-zа-яіїє]+ [A-ZА-ЯІЇЄ][a-zа-яіїє]+$");
    }

    public static boolean isValidHeight(String heightStr) {
        return heightStr != null && heightStr.matches("^[1-2]\\d{2}$");
    }

    public static MyLinkedList<Prisoner> searchByFeatureRegex(MyLinkedList<Prisoner> list, String regex) {
        MyLinkedList<Prisoner> result = new MyLinkedList<>();
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        
        for (Prisoner p : list) {
            for (String feature : p.getFeatures()) {
                if (pattern.matcher(feature).find()) {
                    result.add(p);
                    break;
                }
            }
        }
        return result;
    }
}