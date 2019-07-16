package com.gtechnologies.fishbangla.Fragment;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fxn.stash.Stash;
import com.google.gson.JsonElement;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.GET_LOGIN;
import com.gtechnologies.fishbangla.API_SEND.Send_Product;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Product_Model;
import com.gtechnologies.fishbangla.R;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Upload_Step4 extends Fragment {


    @BindView(R.id.step4_fish_name)
    TextView step4FishName;
    @BindView(R.id.step4_fish_description)
    TextView step4FishDescription;
    @BindView(R.id.step4_fish_category)
    TextView step4FishCategory;
    @BindView(R.id.step4_fish_price)
    TextView step4FishPrice;
    @BindView(R.id.step4_fish_name_quantity)
    TextView step4FishNameQuantity;
    @BindView(R.id.step4_fish_min_quantity)
    TextView step4FishMinQuantity;
    @BindView(R.id.step4_fish_upozila)
    TextView step4FishUpozila;
    @BindView(R.id.step4_fish_selltype)
    TextView step4FishSelltype;
    @BindView(R.id.step4_fish_frozen)
    TextView step4FishFrozen;
    @BindView(R.id.upload_next4)
    Button uploadNext4;
    @BindView(R.id.upload_video_4)
    ImageView uploadVideo4;
    @BindView(R.id.upload_image_4)
    ImageView uploadImage4;
    @BindView(R.id.video_view)
    FrameLayout videoView;
    Unbinder unbinder;

    View view;
    Utility utility;
    Context context;
    ApiInterface apiService;
    GET_LOGIN login;
    Send_Product product;
    File product_image;
    File product_video;
    String image_url;
    String video_url;
    Product_Model productModel;
    public Fragment_Upload_Step4() {
        // Required empty public constructor
    }

    ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment__upload__step4, container, false);
        unbinder = ButterKnife.bind(this, view);
        pager = (ViewPager) getActivity().findViewById(R.id.upload_viewPager);
        try {
            context = getActivity();
            utility = new Utility(context);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            login = (GET_LOGIN) Stash.getObject("user", GET_LOGIN.class);
            product = (Send_Product) Stash.getObject("product", Send_Product.class);
            productModel = (Product_Model) Stash.getObject("product_hint", Product_Model.class);
            if (product != null && productModel != null) {
                //step4FishName.setVisibility(View.GONE);
                step4FishName.setText(productModel.getName());
                step4FishDescription.setText(product.getDescription());
                step4FishCategory.setText(productModel.getCategory());
                step4FishPrice.setText(utility.convertToBangle(product.getPrice()) + context.getResources().getString(R.string.unit_string));
                step4FishNameQuantity.setText(utility.convertToBangle(product.getQuantity()) + context.getResources().getString(R.string.kg_string));
                step4FishMinQuantity.setText(utility.convertToBangle(product.getMinQty()) + context.getResources().getString(R.string.kg_string));
                step4FishUpozila.setText(productModel.getUpozilla());
                step4FishSelltype.setText(productModel.getSell());
                step4FishFrozen.setText(productModel.getFroze());
            }

            image_url = Stash.getString("image_id");
            utility.logger("image=" + image_url);
            if (!TextUtils.isEmpty(image_url)) {
                product_image = new File(image_url);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.loader);
                requestOptions.error(R.drawable.ic_default_background_banner);

                Glide.with(context).load(product_image).thumbnail(0.1f).apply(requestOptions).into(uploadImage4);
            } else {
                uploadImage4.setVisibility(View.GONE);
            }
            video_url = Stash.getString("video_id");
            utility.logger("ABir Video"+video_url);
            if (!TextUtils.isEmpty(video_url)) {
                product_video = new File(video_url);
                final Bitmap thumbnail = getThumbnail();
                if (thumbnail != null) {
                    uploadVideo4.setImageBitmap(thumbnail);
                }
            } else {
                uploadVideo4.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return view;
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                    });
            utility.setFonts_normal(
                    new View[]{
                            step4FishName,
                            step4FishDescription,
                            step4FishCategory,
                            step4FishPrice,
                            step4FishNameQuantity,
                            step4FishMinQuantity,
                            step4FishUpozila,
                            step4FishSelltype,
                            step4FishFrozen,
                            uploadNext4
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void Replace_Fragment(Fragment frag) {
        try {
            if (frag != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.home_area, frag);
                ft.addToBackStack(null);
                ft.commit();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /*@OnClick(R.id.upload_next4)
    public void onViewClicked() {
        if (product != null) {
            Add_product(product);
        } else {
            utility.showToast(getResources().getString(R.string.validation_string));
        }
    }*/

    public void Add_product(Send_Product f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (f != null) {
                utility.showProgress(false);
                Log.d("Upload", "Upload Product" + f.toString());
                Call<JsonElement> call = apiService.FishBangla_Fish_add(f);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Upload Product", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Log.d("Json", response.body().toString());
                                JSONObject jObj = new JSONObject(response.body().toString());
                                String id = jObj.getString("id");
                                if (id != null) {
                                    Stash.clear("product");
                                    Stash.clear("image_id");
                                    Stash.clear("product_hint");
                                    Stash.clear("video_id");
                                    if (product_image != null) {
                                        Image_Upload(id, product_image);
                                    } else {
                                        //utility.showToast(getResources().getString(R.string.product_succes_string));
                                        getActivity().finish();
                                    }
                                    utility.showToast(getResources().getString(R.string.product_succes_string));
                                } else {
                                    utility.showToast(getResources().getString(R.string.try_again_string));
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                            Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error Fish List", t.toString());
                        Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
                        utility.hideProgress();
                    }
                });
            } else {
                Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            utility.hideProgress();
        }
    }

    public void Image_Upload(final String i, /*String url*/File url) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (i != null && url != null) {
                utility.showProgress(false);
                Log.d("Upload", "Upload Product" + url.toString());
                //File m = new File(url);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), url);
                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part image_body = MultipartBody.Part.createFormData("productPhoto", url.getName(), requestFile);
                Call<JsonElement> call = apiService.FishBangla_Product_image_upload(i, image_body);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Upload Product", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Log.d("Json", response.body().toString());
                                JSONObject jObj = new JSONObject(response.body().toString());
                                String id = jObj.getString("code");
                                if (id.equals("200")) {
                                    if (product_video != null) {
                                        Video_Upload(i, product_video);
                                    } else {
                                        //utility.showToast(getResources().getString(R.string.product_succes_string));
                                        getActivity().finish();
                                    }
                                    //utility.showToast(getResources().getString(R.string.product_succes_string));
                                    //getActivity().finish();
                                    //String image_url=Stash.getString("image_id");
                                    //
                                } else {
                                    utility.showToast(getResources().getString(R.string.try_again_string));
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                            Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error Fish List", t.toString());
                        Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
                        utility.hideProgress();
                    }
                });
            } else {
                Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            utility.hideProgress();
        }
    }


    public void Video_Upload(String i, /*String url*/File url) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (i != null && url != null) {
                utility.showProgress(false);
                Log.d("Upload", "Upload Product Video" + url.toString());
                //File m = new File(url);
                RequestBody requestFile = RequestBody.create(MediaType.parse("video/*"), url);
                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part image_body = MultipartBody.Part.createFormData("productVideo", url.getName(), requestFile);
                Call<JsonElement> call = apiService.FishBangla_Product_video_upload(i, image_body);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Upload Product", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Log.d("Json", response.body().toString());
                                JSONObject jObj = new JSONObject(response.body().toString());
                                String id = jObj.getString("code");
                                if (id.equals("200")) {
                                    utility.showToast(getResources().getString(R.string.product_succes_string));
                                    getActivity().finish();
                                    //String image_url=Stash.getString("image_id");
                                    //
                                } else {
                                    utility.showToast(getResources().getString(R.string.try_again_string));
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                            Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error Fish List", t.toString());
                        Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
                        utility.hideProgress();
                    }
                });
            } else {
                Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            utility.hideProgress();
        }
    }

    private Bitmap getThumbnail() {
        if (video_url == null) return null;
        return ThumbnailUtils.createVideoThumbnail(video_url, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }

    public void playVideo() {
        if (video_url == null) return;

        final Intent videoIntent = new Intent(Intent.ACTION_VIEW);
        videoIntent.setDataAndType(Uri.parse(video_url), "video/*");
        try {
            startActivity(videoIntent);
        } catch (ActivityNotFoundException e) {
            // NOP
        }
    }

    @OnClick(R.id.VideoPreviewPlayButton)
    public void onUploadVideo4Clicked() {
        playVideo();
    }

    @OnClick(R.id.upload_next4)
    public void onUploadNext4Clicked() {
        if (product != null) {
            Add_product(product);
        } else {
            utility.showToast(getResources().getString(R.string.validation_string));
        }
    }
}
