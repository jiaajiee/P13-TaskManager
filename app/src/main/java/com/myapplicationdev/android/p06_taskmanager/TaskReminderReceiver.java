package com.myapplicationdev.android.p06_taskmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

public class TaskReminderReceiver extends BroadcastReceiver {

    int notifReqCode = 123;

	@Override
	public void onReceive(Context context, Intent i) {

		int id = i.getIntExtra("id", -1);
		String name = i.getStringExtra("name");
		String desc = i.getStringExtra("desc");

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

		NotificationCompat.Action action = new
				NotificationCompat.Action.Builder(
				R.mipmap.ic_launcher,
				desc,
				pIntent).build();

		Intent intentReply = new Intent(context, MainActivity.class);
		intentReply.putExtra("key", id);

		PendingIntent pendingIntentReply = PendingIntent.getActivity
				(context, 0, intentReply,
						PendingIntent.FLAG_UPDATE_CURRENT);

		RemoteInput ri = new RemoteInput.Builder("status")
				.setLabel("Status report")
				.setChoices(new String [] {"Completed", "Not yet"})
				.build();

		NotificationCompat.Action action2 = new
				NotificationCompat.Action.Builder(
				R.mipmap.ic_launcher,
				"Reply",
				pendingIntentReply)
				.addRemoteInput(ri)
				.build();

		RemoteInput add = new RemoteInput.Builder("add")
				.setLabel("Add new tasks")
				.build();


//		NotificationCompat.Action action3 = new
//				NotificationCompat.Action.Builder(
//				R.mipmap.ic_launcher,
//				"Add new task",
//				pendingIntentReply)
//				.addRemoteInput(add)
//				.build();



		NotificationCompat.WearableExtender extender = new
				NotificationCompat.WearableExtender();
		extender.addAction(action);
		extender.addAction(action2);
//		extender.addAction(action3);

		Notification notification = new
				NotificationCompat.Builder(context)
				.setContentText(name + "\n" + desc)
				.setContentTitle("Task")
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentIntent(pIntent)
				// When Wear notification is clicked, it performs
				// the action we defined in line below
				.extend(extender)
				.build();

		NotificationManagerCompat notificationManagerCompat =
				NotificationManagerCompat.from(context);
		notificationManagerCompat.notify(notifReqCode, notification);


//		// build notification
//		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//		builder.setContentTitle("Task Manager Reminder");
//		builder.setContentText(name);
//		builder.setSmallIcon(android.R.drawable.ic_dialog_info);
//		builder.setContentIntent(pIntent);
//		builder.setAutoCancel(true);
//
//		Notification n = builder.build();
//
//
//
//		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//		// You may put an ID for the first parameter if you wish
//		// to locate this notification to cancel
//		notificationManager.notify(notifReqCode, n);
	}

}
