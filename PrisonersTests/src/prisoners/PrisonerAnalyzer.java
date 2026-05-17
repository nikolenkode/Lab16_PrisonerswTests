package prisoners;

import container.MyLinkedList;

public class PrisonerAnalyzer {

    public static Prisoner findTallestPrisoner(MyLinkedList<Prisoner> list) {
        if (list.isEmpty()) return null;
        
        Prisoner tallest = list.get(0);
        for (Prisoner p : list) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("[Поток 1] ЗУПИНЕНО за таймаутом!");
                return null;
            }
            try { 
                Thread.sleep(30); 
            } catch (InterruptedException e) { 
                System.out.println("[Поток 1] Прервано во время ожидания!");
                return null; 
            }

            if (p.getHeight() > tallest.getHeight()) {
                tallest = p;
            }
        }
        return tallest;
    }

    public static double calculateAverageHeight(MyLinkedList<Prisoner> list) {
        if (list.isEmpty()) return 0.0;
        
        double sum = 0;
        int count = 0;
        for (Prisoner p : list) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("[Поток 2] ЗУПИНЕНО за таймаутом!");
                return 0.0;
            }
            try { 
                Thread.sleep(30); 
            } catch (InterruptedException e) { 
                System.out.println("[Поток 2] Прервано во время ожидания!");
                return 0.0; 
            }

            sum += p.getHeight();
            count++;
        }
        return sum / count;
    }

    public static int countByEyeColor(MyLinkedList<Prisoner> list, String eyeColor) {
        int count = 0;
        for (Prisoner p : list) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("[Поток 3] ЗУПИНЕНО за таймаутом!");
                return 0;
            }
            try { 
                Thread.sleep(30); 
            } catch (InterruptedException e) { 
                System.out.println("[Поток 3] Прервано во время ожидания!");
                return 0; 
            }

            if (p.getEyeColor().equalsIgnoreCase(eyeColor)) {
                count++;
            }
        }
        return count;
    }
}