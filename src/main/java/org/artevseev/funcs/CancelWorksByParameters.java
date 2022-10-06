package org.artevseev.funcs;

import org.artevseev.Pages.HomeworksPage;
import org.artevseev.Pages.OneHomeworkPage;
import org.artevseev.links.Parameters;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

public class CancelWorksByParameters {

    private static void cancelFirstWorksByParameters(WebDriver driver, Parameters parameters, Date deadline){

        try {
            driver.get(parameters.generateLink());
        } catch (TimeoutException ignore){
            System.out.println("Timeout with getting first hw");
        }

        HomeworksPage homeworksPage = new HomeworksPage(driver);

        if(homeworksPage.getFirstHwDate().after(deadline)){
            System.out.println(homeworksPage.getFirstHwDate());
            homeworksPage.clickFirstHwButton(); //  Open first hw
            OneHomeworkPage oneHomeworkPage = new OneHomeworkPage(driver);

            oneHomeworkPage.cancelHW();
        }
        else {
            throw new NoSuchElementException("Все работы после дедлайна отклонены!");
        }

    }

    public static void cancelAllWorks(WebDriver driver, Parameters parameters, Date deadline){

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3500));

        int chet = 0;

        while (true) {
            try {
                cancelFirstWorksByParameters(driver, parameters, deadline);
                System.out.println(Thread.currentThread().getName() + ": " + LocalTime.now() + " Работ отклонено: " + ++chet);
            }catch (NoSuchElementException e){
                System.out.println(Thread.currentThread().getName() + " " + LocalTime.now() + ": Идёт поиск работ");
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException ex) {
                    System.out.println(Thread.currentThread() + " " + LocalTime.now() + ": не засыпает(((");
                }
            }
        }
    }


}
