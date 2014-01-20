/*
 * Copyright (C) 2013 Evgeny Shishkin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.vang.progressswitcher.sample;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

/**
 * @author Evgeny Shishkin
 */
public class ProgressActivity extends FragmentActivity {
    public static final String EXTRA_TITLE = "ru.vang.progressfragment.sample.extras.EXTRA_TITLE";
    public static final String EXTRA_FRAGMENT = "ru.vang.progressfragment.sample.extras.EXTRA_FRAGMENT";
    public static final int FRAGMENT_FROM_ROOT = 0;
    public static final int FRAGMENT_FROM_CONTENT = 1;
    public static final int FRAGMENT_PROGRESS_WIDGET = 2;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(EXTRA_TITLE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Check what fragment is shown, replace if needed.
        Fragment fragment = getSupportFragmentManager().findFragmentById(
                android.R.id.content);
        if (fragment == null) {
            // Make new fragment to show.
            int fragmentId = getIntent().getIntExtra(EXTRA_FRAGMENT, FRAGMENT_FROM_ROOT);
            switch (fragmentId) {
                case FRAGMENT_FROM_ROOT:
                    fragment = ProgressSwitcherFromRootFragment.newInstance();
                    break;
                case FRAGMENT_FROM_CONTENT:
                    fragment = ProgressSwitcherFromContentFragment.newInstance();
                    break;
                case FRAGMENT_PROGRESS_WIDGET:
                    fragment = ProgressWidgetFragment.newInstance();
                    break;
                default:
                    throw new IllegalArgumentException("Incorrect index: " + fragmentId);

            }
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}