package info.jackrex.v2ex;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends BaseActivity {

	private WebView webView;
	private WebChromeClient chromeClient;
	private WebViewClient webViewClient;
	private Intent intent;

	
	
	@Override
	public void setLayoutAndInitView() {
		// TODO Auto-generated method stub
	
		setContentView(R.layout.activity_webview);
		webView=(WebView) findViewById(R.id.webView1);
		initWebView();
	}     
	
	

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {
		// TODO Auto-generated method stub
		intent=getIntent();
	//	webView.setWebChromeClient(chromeClient);
		
		webViewClient=new MyWebViewClient();
		webView.setWebViewClient(webViewClient);
		
		
		chromeClient=new WebChromeClient(){
			@Override
			public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
				// TODO Auto-generated method stub
				return super.onConsoleMessage(consoleMessage);
			}
			
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
			}
		};
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setRenderPriority(RenderPriority.HIGH);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setLoadsImagesAutomatically(true);
		
		
		loadUrl(intent.getStringExtra("url"));
		//loadUrl("http://127.0.0.1:8080");
		
	}
	
	private void loadUrl(String url){
		webView.loadUrl(url);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(webView.canGoBack()&&keyCode==KeyEvent.KEYCODE_BACK){
			webView.goBack();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	class MyWebViewClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}
	}
	



	
}
