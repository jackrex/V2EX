package info.jackrex.v2ex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

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
	public static String fileDir="";
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setupUIL();
		fileDir=getFilesDir().toString();
	}
	
	
	
	private void setupUIL() {
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "V2EX/cache");
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
	
	
	public static <T> void writeToFile(String filename,List<T>... lists) {
		// TODO Auto-generated method stub
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(fileDir+"/" + filename));
			
			for(List<T> list:lists){
				out.writeObject(list);
			}
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static <T> void writeToFile(String filename,T... data) {
		// TODO Auto-generated method stub
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(fileDir+"/" + filename));
			
			for(T list:data){
				out.writeObject(list);
			}
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
