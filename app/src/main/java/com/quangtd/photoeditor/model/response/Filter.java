package com.quangtd.photoeditor.model.response;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.quangtd.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHalftoneFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLaplacianFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNonMaximumSuppressionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSmoothToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

/**
 * QuangTD on 12/2/2017.
 */

public enum Filter {
    NONE,
    BRIGHTNESS,
    AMATORKA,
    BMW,
    BEAUTY,
    VIGNETTE,
    MONOCHROME,
    WHITE_BALANCE,
    SWIRL,
    LAPLACIAN,
    CGA_COLORSPACE,
    DILATION,
    KUWAHARA,
    RGB_DILATION,
    SKETCH,
    TOON,
    SMOOTH_TOON,
    BULGE_DISTORTION,
    HAZE,
    NON_MAXIMUM_SUPPRESSION,
    FALSE_COLOR,
    COLOR_BALANCE,
    LEVELS_FILTER_MIN,
    HALFTONE;

    public static List<Filter> createFilterList() {
        List<Filter> filters = new ArrayList<>();
        filters.add(NONE);
        filters.add(BRIGHTNESS);
        filters.add(AMATORKA);
        filters.add(BMW);
        filters.add(BEAUTY);
        filters.add(VIGNETTE);
        filters.add(MONOCHROME);
        filters.add(WHITE_BALANCE);
        filters.add(SWIRL);
        filters.add(LAPLACIAN);
        filters.add(CGA_COLORSPACE);
        filters.add(DILATION);
        filters.add(KUWAHARA);
        filters.add(RGB_DILATION);
        filters.add(SKETCH);
        filters.add(TOON);
        filters.add(SMOOTH_TOON);
        filters.add(BULGE_DISTORTION);
        filters.add(HAZE);
        filters.add(NON_MAXIMUM_SUPPRESSION);
        filters.add(FALSE_COLOR);
        filters.add(COLOR_BALANCE);
        filters.add(LEVELS_FILTER_MIN);
        filters.add(HALFTONE);
        return filters;
    }

    private static GPUImageFilter amatorkaFilter(Context context) {
        GPUImageLookupFilter lookupFilter = new GPUImageLookupFilter();
        lookupFilter.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.lookup_amatorka));
        return lookupFilter;
    }

    private static GPUImageFilter beautyFilter() {
        GPUImageSharpenFilter sharpness = new GPUImageSharpenFilter();
        sharpness.setSharpness(2.0f);
        return sharpness;
    }

    public static GPUImageFilter getGpuFilter(Context context, Filter filter) {
        GPUImageFilter gpuImageFilter = null;
        switch (filter) {
            case NONE:
                gpuImageFilter = new GPUImageFilter();
                break;
            case AMATORKA:
                gpuImageFilter = amatorkaFilter(context);
                break;
            case BEAUTY:
                gpuImageFilter = beautyFilter();
                break;
            case BMW:
                gpuImageFilter = new GPUImageGrayscaleFilter();
                break;
            case SWIRL:
                gpuImageFilter = new GPUImageSwirlFilter();
                break;
            case BRIGHTNESS:
                gpuImageFilter = new GPUImageBrightnessFilter(0.3f);
                break;
            case LAPLACIAN:
                gpuImageFilter = new GPUImageLaplacianFilter();
                break;
            case MONOCHROME:
                gpuImageFilter = new GPUImageMonochromeFilter(1.0f, new float[]{0.6f, 0.45f, 0.3f, 1.0f});
                break;
            case CGA_COLORSPACE:
                gpuImageFilter = new GPUImageCGAColorspaceFilter();
                break;
            case VIGNETTE:
                gpuImageFilter = new GPUImageVignetteFilter(
                        new PointF(0.5f, 0.5f),
                        new float[]{0.0f, 0.0f, 0.0f},
                        0.2f,
                        0.75f);
                break;
            case WHITE_BALANCE:
                gpuImageFilter = new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
                break;
            case DILATION:
                gpuImageFilter = new GPUImageDilationFilter();
                break;
            case KUWAHARA:
                gpuImageFilter = new GPUImageKuwaharaFilter();
                break;
            case RGB_DILATION:
                gpuImageFilter = new GPUImageRGBDilationFilter();
                break;
            case SKETCH:
                gpuImageFilter = new GPUImageSketchFilter();
                break;
            case TOON:
                gpuImageFilter = new GPUImageToonFilter();
                break;
            case SMOOTH_TOON:
                gpuImageFilter = new GPUImageSmoothToonFilter();
                break;

            case BULGE_DISTORTION:
                gpuImageFilter = new GPUImageBulgeDistortionFilter();
                break;
            case HAZE:
                gpuImageFilter = new GPUImageHazeFilter();
                break;
            case NON_MAXIMUM_SUPPRESSION:
                gpuImageFilter = new GPUImageNonMaximumSuppressionFilter();
                break;
            case FALSE_COLOR:
                gpuImageFilter = new GPUImageFalseColorFilter();
                break;
            case COLOR_BALANCE:
                gpuImageFilter = new GPUImageColorBalanceFilter();
                break;

            case LEVELS_FILTER_MIN:
                GPUImageLevelsFilter levelsFilter = new GPUImageLevelsFilter();
                levelsFilter.setMin(0.0f, 3.0f, 1.0f);
                gpuImageFilter = levelsFilter;
                break;
            case HALFTONE:
                gpuImageFilter = new GPUImageHalftoneFilter();
                break;
        }
        return gpuImageFilter;
    }
}
