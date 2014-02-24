package info.jackrex.v2ex;

import java.io.File;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.DiscCacheAware;
import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class VApplication extends Application {

	public static DisplayImageOptions options;
	public static DiscCacheAware cache;
	
	public static String tab = "tech";
	public static  String[] tabs = {"tech","creative","play","apple","jobs","deals","city","qna","hot","all"};
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setupUIL();
	}
	
	
	
	private void setupUIL() {
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "CafeCar/cache");
		options = new DisplayImageOptions.Builder()
				.cacheOnDisc(true).cacheInMemory(true).build();
		cache = new TotalSizeLimitedDiscCache(cacheDir, 50 * 1024 * 1024);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache implementation
				.discCache(cache)
				// You can pass your own disc cache implementation
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.defaultDisplayImageOptions(options).build();
		ImageLoader.getInstance().init(config);
	}
	
}
