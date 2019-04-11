package hackidc.com.tight;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AuthenticationDialog extends AppCompatActivity {
    private String TAG = AuthenticationDialog.class.getSimpleName();
    //private AuthenticationListener listener;
    private Context context;
    private WebView webView;

    private final String url = "https://api.instagram.com/" + "oauth/authorize/?client_id=" +
            "fe02108af0584ed9a91a20a40765fc47"
            + "&redirect_url="
            + "http%3A%2F%2Flocalhost%3A8080"
            + "&response_type=token";

    //public AuthenticationDialog(@NonNull Context context/*, AuthenticationListener listener*/) {
        //super(context);

        //this.context = context;
        //this.listener = listener;
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.auth_dialog);
        initializeWebView();
    }

    private void initializeWebView() {
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        clearCookies(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        //webView.loadUrl("http://api.instagram.com/");
        //webView.loadUrl("https://api.instagram.com/oauth/authorize/?client_id=af7efcca661e43459b1e502af7ddb689&redirect_uri=https://instagram.com/&response_type=token&scope=basic+public_content");
        webView.setWebViewClient(new WebViewClient() {

            String access_token;
            boolean authComplete;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("Page Started");
            }



            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("Page finished");
                /*if (url.contains("#access_token=") && !authComplete) {
                    Log.d(TAG, " inside access_token");
                    Uri uri = Uri.parse(url);
                    access_token = uri.getEncodedFragment();
                    //get the whole token after "=" sign
                    access_token = access_token.substring(access_token.lastIndexOf("=") + 1);
                    Log.d(TAG, "token: " + access_token);
                    authComplete = true;
                    //listener.onCodeReceived(access_token);
                    finish();
                    //dismiss();
                } else if (url.contains("?error")) {
                    Log.d(TAG, "getting error fetching access token");
                    finish();
                    //dismiss();
                } else {
                    Log.d(TAG, "outside both: " + url.toString());
                }*/
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(AuthenticationDialog.this, ViewPagerActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @SuppressWarnings("deprecation")
    public static void clearCookies(Context context)
    {
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();

    }
}