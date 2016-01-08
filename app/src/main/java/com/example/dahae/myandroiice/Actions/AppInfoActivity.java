package com.example.dahae.myandroiice.Actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.NewPlan.NewPlanAction;
import com.example.dahae.myandroiice.R;

public class AppInfoActivity extends Activity {

	private PackageManager pm;

	private View mLoadingContainer;
	private ListView mListView = null;
	private IAAdapter mAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_info);

		mLoadingContainer = findViewById(R.id.loading_container);
		mListView = (ListView) findViewById(R.id.listView1);

		mAdapter = new IAAdapter(this);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				String package_name = ((TextView) view.findViewById(R.id.app_package)).getText().toString();

				Intent intent = getIntent();
				intent.putExtra("mActionInfo", package_name.toString());
				intent.setAction("Bookmark");
				setResult(RESULT_OK, intent);
				finish();
			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				this.finish();
				break;
			}
		}
		this.finish();
	}

	@Override
	protected void onResume() {
		super.onResume();

		startTask();
	}

	private void startTask() {
		new AppTask().execute();
	}

	private void setLoadingView(boolean isView) {
		if (isView) {

			mLoadingContainer.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.GONE);
		} else {

			mListView.setVisibility(View.VISIBLE);
			mLoadingContainer.setVisibility(View.GONE);
		}
	}

	private class ViewHolder {
		public ImageView mIcon;
		public TextView mName;
		public TextView mPacakge;
	}

	private class IAAdapter extends BaseAdapter {
		private Context mContext = null;

		private List<ApplicationInfo> mAppList = null;
		private ArrayList<AppInfo> mListData = new ArrayList<AppInfo>();

		public IAAdapter(Context mContext) {
			super();
			this.mContext = mContext;
		}

		public int getCount() {
			return mListData.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				holder = new ViewHolder();

				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				convertView = inflater.inflate(R.layout.list_item_layout, null);
				holder.mIcon = (ImageView) convertView
						.findViewById(R.id.app_icon);
				holder.mName = (TextView) convertView
						.findViewById(R.id.con_name);
				holder.mPacakge = (TextView) convertView
					.findViewById(R.id.app_package);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			AppInfo data = mListData.get(position);

			if (data.mIcon != null) {
				holder.mIcon.setImageDrawable(data.mIcon);
			}

			holder.mName.setText(data.mAppNaem);
			holder.mPacakge.setText(data.mAppPackge);

			return convertView;
		}

		public void rebuild() {
			if (mAppList == null) {

				Log.d(MainActivity.TAG + AppInfoActivity.class.getSimpleName(), "Is Empty Application List");

				pm = AppInfoActivity.this.getPackageManager();

				mAppList = pm
						.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
								| PackageManager.GET_DISABLED_COMPONENTS);
			}

			/*
			 * AppFilter filter; switch (MENU_MODE) { case MENU_DOWNLOAD: filter
			 * = AppInfo.THIRD_PARTY_FILTER; bre:ak; default filter = null;
			 * break; }
			 * 
			 * if (filter != null) { filter.init(); }
			 */

			mListData.clear();

			AppInfo addInfo = null;
			for (ApplicationInfo app : mAppList) {

				addInfo = new AppInfo();

				addInfo.mIcon = app.loadIcon(pm);

				addInfo.mAppNaem = app.loadLabel(pm).toString();

				addInfo.mAppPackge = app.packageName;
				mListData.add(addInfo);
			}
			Collections.sort(mListData, AppInfo.ALPHA_COMPARATOR);
		}
	}

	private class AppTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			setLoadingView(true);
		}

		@Override
		protected Void doInBackground(Void... params) {

			mAdapter.rebuild();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			mAdapter.notifyDataSetChanged();
			setLoadingView(false);
		}
	}

}