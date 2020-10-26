package com.sharkz.largeimage.glide;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.sharkz.largeimage.sdk.ILargeImageView;
import com.sharkz.largeimage.sdk.factory.FileBitmapDecoderFactory;

import java.io.File;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.view.View.Z;

/**
 * A base {@link com.bumptech.glide.request.target.Target} for displaying resources in
 * {@link ImageView}s.
 *
 * @param <Z> The type of resource that this target will display in the wrapped {@link ImageView}.
 */
public class LargeImageViewTarget extends ViewTarget<View, File>{
    private ILargeImageView largeImageView;
    public <V extends View & ILargeImageView> LargeImageViewTarget(V view) {
        super(view);
        this.largeImageView = view;
    }

    /**
     * Sets the given {@link Drawable} on the view using
     * {@link ImageView#setImageDrawable(Drawable)}.
     *
     * @param placeholder {@inheritDoc}
     */
    @Override
    public void onLoadStarted(Drawable placeholder) {
        largeImageView.setImageDrawable(placeholder);
    }

    /**
     * Sets the given {@link Drawable} on the view using
     * {@link ImageView#setImageDrawable(Drawable)}.
     *
     * @param errorDrawable {@inheritDoc}
     */
    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        largeImageView.setImageDrawable(errorDrawable);
    }

    @Override
    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
        largeImageView.setImage(new FileBitmapDecoderFactory(resource));
    }

    /**
     * Sets the given {@link Drawable} on the view using
     * {@link ImageView#setImageDrawable(Drawable)}.
     *
     * @param placeholder {@inheritDoc}
     */
    @Override
    public void onLoadCleared(Drawable placeholder) {
        largeImageView.setImageDrawable(placeholder);
    }
}

