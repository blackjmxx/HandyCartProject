package com.caddieneo;



import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class CaddieMainActivity extends Activity {
	
	public static final String PROTOCOL_SCHEME_RFCOMM = "btspp";
	private static final String TAG = CaddieMainActivity.class.getSimpleName();
	private Handler _handler = new Handler();
	
	/* GetDefaultAdapter */
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	
	/* Server */
	private BluetoothServerSocket _serverSocket;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_caddie_main);
		if (!_bluetooth.isEnabled()) {
			finish();
			return;
		}
		/* Start listen */
		_serverWorker.start();
	}

	
	/* Thread-Listen */
	private Thread _serverWorker = new Thread() {
		public void run() {
			listen();
		};
	};
	
	protected void onDestroy() {
		super.onDestroy();
		shutdownServer();
	}
	protected void finalize() throws Throwable {
		super.finalize();
		shutdownServer();
	}
	
	private void shutdownServer() {
		new Thread() {
			public void run() {
				_serverWorker.interrupt();
				if (_serverSocket != null) {
					try {
						/* close sever */
						_serverSocket.close();
					} catch (IOException e) {
						Log.e("EF-BTBee", "", e);
					}
					_serverSocket = null;
				}
			};
		}.start();
	}
	public void onButtonClicked(View view) {
		shutdownServer();
	}
	
	protected void listen() {
		try {
			/* Create BT service
			 * param��server name��UUID
			 */
	
			_serverSocket = _bluetooth.listenUsingRfcommWithServiceRecord(PROTOCOL_SCHEME_RFCOMM,
					UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666"));
			
			/* Client devices*/
			_handler.post(new Runnable() {
				public void run() {
					
				}
			});
			/* accept client request */
			BluetoothSocket socket = _serverSocket.accept();
			Log.d("EF-BTBee", ">>Accept Client Request");
			
			/* Processing the request content*/
			if (socket != null) {
				while(true){
					
				
				InputStream inputStream = socket.getInputStream();
				byte[] buffer = new byte[1024];
				int bytes;

				bytes = inputStream.read(buffer);
				String readMessage = new String(buffer, 0, bytes);
				Log.d(TAG, "Message :: "+readMessage);
				
				Log.d("EF-BTBee", ">>Server is over!!");
				}
			}
			} catch (IOException e) {
				Log.e("EF-BTBee", "", e);
			} finally {
		}
	}


	

}
