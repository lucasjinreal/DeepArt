package com.ouman.deepart;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.*;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity {

    private TextView tv_rate;
    private FloatingActionsMenu fabMenu;
    private FloatingActionButton addImageButton;
    private FloatingActionButton addArtButton;
    private FloatingActionButton shareButton;
    private ImageView normalImageView;
    private ImageView artImageView;
    private Button generateButton;
    private static final int PICK_REQUEST_ART = 1;
    private static final int PICK_REQUEST_NORMAL = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_rate = (TextView) findViewById(R.id.tv_rate);
        normalImageView = (ImageView) findViewById(R.id.imageViewNormal);
        artImageView = (ImageView) findViewById(R.id.imageViewArt);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_rate);
        seekBar.setMax(100);
        seekBar.setProgress(50);
        tv_rate.setText(String.valueOf(seekBar.getProgress()) + "%");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_rate.setText(String.valueOf(i) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        addImageButton = (FloatingActionButton) findViewById(R.id.fab_add_img);
        addArtButton = (FloatingActionButton) findViewById(R.id.fab_add_art);
        shareButton = (FloatingActionButton) findViewById(R.id.fab_share);
        fabMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.collapse();
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_REQUEST_NORMAL);
            }
        });

        addArtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.collapse();
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_REQUEST_ART);
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.collapse();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("开发者最近比较穷比较忙，没有钱集成朋友圈分享的SDK，建议直接截图分享朋友圈...");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", null);
                builder.setNegativeButton("取消", null);
                builder.show();

            }
        });

        generateButton = (Button) findViewById(R.id.button_generate);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setCancelable(true);
                progressDialog.setMessage("正在运用深度神经网络学习艺术画风格..这可能需要一个多小时，建议你先去买个夜宵或者去网吧打会游戏..");
                progressDialog.show();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(intent);
            return true;
        }else if (id == R.id.action_about){
            Intent intent2 = new Intent(MainActivity.this, AboutActivity.class);
            MainActivity.this.startActivity(intent2);
            return true;
        }else if (id == R.id.action_github){
            Uri uri = Uri.parse("https://lewisjin.coding.me");
            Intent intent3 = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent3);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //调用系统相册选取重写这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_REQUEST_NORMAL:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(MainActivity.this, "取消了从相册选择", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    Uri imageUri = data.getData();
                    System.out.println(imageUri);
                    normalImageView.setImageURI(imageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PICK_REQUEST_ART:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(MainActivity.this, "取消了从相册选择", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    Uri imageUri = data.getData();
                    System.out.println(imageUri);
                    artImageView.setImageURI(imageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
