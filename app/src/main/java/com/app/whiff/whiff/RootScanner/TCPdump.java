package com.app.whiff.whiff.RootScanner;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.app.whiff.whiff.R;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Executes TCPdump in a shell
 */


public class TCPdump extends ContextWrapper {

    public Context context;
    public Handler handler;

    public static final String TCPdumpBinaryPath = "/data/data/com.app.whiff.whiff/files/";

    public TCPdump(Context base, Handler handler) {
        super(base);
        context = base;
        this.handler = handler;
    }

    public void installTCPdump() {

        new Thread(new Runnable() { // So that UI thread is not blocked by su calls.
            @Override
            public void run() {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    Log.d("TCPdump", "Running on main thread");
                } else {
                    Log.d("TCPdump", "Not running on main thread");

                    // Install TCPdump if not already installed
                    if (RootTools.isAccessGiven()) {
                        RootTools.installBinary(context, R.raw.tcpdump, "tcpdump");
                    }
                }
            }
        }).start();
    }

    public void doSniff() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Command command = new Command(0, "cd " + TCPdumpBinaryPath, "./tcpdump --list-interfaces") {
                    // Command command = new Command(0, "tcpdump -i wlan0 -vvv") {
                    @Override
                    public void commandOutput(int id, final String line) {
                        System.out.println(line);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Bundle bundle = new Bundle();
                                bundle.putString("key",line);
                                Message message = new Message();
                                message.setData(bundle);
                                message.setTarget(handler);
                                message.sendToTarget();
                            }
                        });
                        super.commandOutput(id, line);
                    }

                    @Override
                    public void commandTerminated(int id, String reason) {
                        System.out.println(reason);
                        super.commandTerminated(id, reason);
                    }

                    @Override
                    public void commandCompleted(int id, int exitcode) {
                        System.out.println(exitcode);
                        super.commandCompleted(id, exitcode);
                    }
                };
                try {
                    RootTools.getShell(true).add(command);
                } catch (IOException | RootDeniedException | TimeoutException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void stopSniff() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Command command = new Command(0, "killall tcpdump") {
                    @Override
                    public void commandOutput(int id, final String line) {
                        System.out.println(line);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Bundle bundle = new Bundle();
                                bundle.putString("key",line);
                                Message message = new Message();
                                message.setData(bundle);
                                message.setTarget(handler);
                                message.sendToTarget();
                            }
                        });
                        super.commandOutput(id, line);
                    }

                    @Override
                    public void commandTerminated(int id, String reason) {
                        System.out.println(reason);
                        super.commandTerminated(id, reason);
                    }

                    @Override
                    public void commandCompleted(int id, int exitcode) {
                        System.out.println(exitcode);
                        super.commandCompleted(id, exitcode);
                    }
                };
                try {
                    RootTools.getShell(true).add(command);
                } catch (IOException | RootDeniedException | TimeoutException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}