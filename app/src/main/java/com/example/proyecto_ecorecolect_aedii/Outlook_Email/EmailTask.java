package com.example.proyecto_ecorecolect_aedii.Outlook_Email;

import android.content.Context;
import android.os.AsyncTask;

public class EmailTask extends AsyncTask<Void, Void, Void> {
    private String recipient;
    private String subject;
    private String body;

    private Context context;
    public EmailTask(String recipient, String subject, String body, Context context) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        EmailSender.sendEmail(recipient, subject, body, context);
        return null;
    }
}
