package info.pf91.scripturable;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GooglePageFragment extends Fragment {
	
	private WebView mWebView; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		
		} 
	
	@Override
	@SuppressLint("SetJavaScriptEnabled")
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) { 
		View v = inflater.inflate( R.layout.fragment_web_page, parent, false); 
		mWebView = (WebView)v.findViewById( R.id.webView); 
	
		mWebView = (WebView)v.findViewById(R.id.webView);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl("http://www.google.com");
		mWebView.setWebViewClient(new WebViewClient());

		return v; 
		}

}
