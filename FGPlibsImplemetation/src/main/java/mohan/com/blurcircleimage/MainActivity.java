package mohan.com.blurcircleimage;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.squareup.picasso.Picasso;

import jp.wasabeef.fresco.processors.BlurPostprocessor;
import mohan.com.blurcircleimage.picasso.PicassoCircleTransformation;

public class MainActivity extends AppCompatActivity {

    String imgUrl0="http://i.imgur.com/DvpvklR.png";
    String imgUrl1="https://am22.akamaized.net/tms/cnt/uploads/2018/09/avengers-4-promo-leak.jpg";
    String imgUrl2="https://cdn.pinkvilla.com/files/styles/contentpreview/public/Avengers%204%20Here%27s%20why%20Robert%20Downey%20Jrs%20film%20trailer%20could%20be%20out%20on%20November%2028.jpg?itok=c-f32XPv";
    String imgUrl3="http://cinema.com/image_lib/CartoonNetwork.jpg";
    ImageView picassoImageView,glideImageView,glideImageView_blur;
    SimpleDraweeView sdv_image,sdv_image_circle,sdv_my_image_view;
    Postprocessor postprocessor;
    ImageRequest imageRequest;
    PipelineDraweeController controller;
    public final int BLUR_PRECENTAGE = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize fresco
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        initViews();
        configFrescoAllOptions();
        configFrescoCircle();
        configFrescoBlur();
        configPicasso();
    }




    private void initViews() {
        picassoImageView= findViewById(R.id.picassoImageView);
        glideImageView= findViewById(R.id.glideImageView);
        glideImageView_blur= findViewById(R.id.glideImageView_blur);
        sdv_image =findViewById(R.id.sdv_image);
        sdv_image_circle=findViewById(R.id.sdv_image_circle);
        sdv_my_image_view=findViewById(R.id.sdv_my_image_view);
    }

    private void configFrescoAllOptions() {
        Uri imageUri = Uri.parse(imgUrl3);
        sdv_my_image_view.setImageURI(imageUri);
    }

    private void configFrescoCircle() {
        Uri imageUri = Uri.parse(imgUrl1);
        sdv_image_circle.setImageURI(imageUri);
    }

    private void configFrescoBlur() {
        postprocessor = new BlurPostprocessor(this,BLUR_PRECENTAGE);
        imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imgUrl2))
                .setPostprocessor(postprocessor)
                .build();
        controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(sdv_image.getController())
                .build();
        sdv_image.setController(controller);
    }

    private void configPicasso(){
        Picasso.with(getApplicationContext()).load(imgUrl0)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(new PicassoCircleTransformation())
                .into(picassoImageView);


    }
}

/*Reference Url*/
/* https://android.jlelse.eu/android-image-blur-using-fresco-vs-picasso-ea095264abbf  */
/* https://stackoverflow.com/questions/40771105/i-have-problems-with-circle-image-in-fresco */
/*https://guides.codepath.com/android/displaying-images-with-the-fresco-library*/




/*Glide.with(getApplicationContext()).load("http://i.imgur.com/DvpvklR.png")
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .bitmapTransform(new GlideCircleTransformation(getApplicationContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) findViewById(R.id.glideImageView));*/

        /*Glide.with(getApplicationContext()).load(imgUrl0).asBitmap().centerCrop().dontAnimate().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(new BitmapImageViewTarget(glideImageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                glideImageView.setImageDrawable(circularBitmapDrawable);
            }
        });*/