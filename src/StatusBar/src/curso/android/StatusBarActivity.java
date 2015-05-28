package curso.android;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

public class StatusBarActivity extends Activity {

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    final Button notificameSimple = (Button) this.findViewById(R.Id.Simple);
    final Button notificamePersonalizada = (Button) this
        .findViewById(R.Id.Personalizada);

    notificameSimple.setOnClickListener(new OnClickListener() {

      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        final NotificationManager mNotificationManager = (NotificationManager) StatusBarActivity.this
            .getSystemService(Context.NOTIFICATION_SERVICE);

        final int icon = R.drawable.icon;
        final CharSequence tickerText = "Hello";
        final long when = System.currentTimeMillis();

        final Notification notification = new Notification(icon, tickerText,
            when);

        final Context context = StatusBarActivity.this.getApplicationContext();
        final CharSequence contentTitle = "My notification";
        final CharSequence contentText = "Hello World!";
        final Intent notificationIntent = new Intent(StatusBarActivity.this,
            StatusBarActivity.class);
        final PendingIntent contentIntent = PendingIntent.getActivity(
            StatusBarActivity.this, 0, notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText,
            contentIntent);

        mNotificationManager.notify(1, notification);
      }
    });

    notificamePersonalizada.setOnClickListener(new OnClickListener() {

      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        final NotificationManager mNotificationManager = (NotificationManager) StatusBarActivity.this
            .getSystemService(Context.NOTIFICATION_SERVICE);

        final int icon = R.drawable.icon;
        final CharSequence tickerText = "Hi";
        final long when = System.currentTimeMillis();

        final Notification notification = new Notification(icon, tickerText,
            when);

        final RemoteViews contentView = new RemoteViews(StatusBarActivity.this
            .getPackageName(), R.layout.custom_notification_layout);
        contentView.setImageViewResource(R.id.image, R.drawable.icon);
        contentView.setTextViewText(R.id.text,
            "Hello, this message is in a custom expanded view");
        notification.contentView = contentView;

        final Intent notificationIntent = new Intent(StatusBarActivity.this,
            StatusBarActivity.class);
        final PendingIntent contentIntent = PendingIntent.getActivity(
            StatusBarActivity.this, 0, notificationIntent, 0);
        notification.contentIntent = contentIntent;

        mNotificationManager.notify(1, notification);
      }
    });
  }
}