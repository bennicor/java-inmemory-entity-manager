package org.example.shop;

import org.example.shop.cli.ApplicationContext;
import org.example.shop.cli.CliApplication;
import org.example.shop.cli.core.Context;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Context ctx = ApplicationContext.getContext(new Scanner(System.in));

        CliApplication cliApp = new CliApplication(ctx);
        cliApp.run();
    }
}
