/*
 * OpenCV for Android NDK
 * Copyright (c) 2006-2009 SIProp Project http://www.siprop.org/
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it freely,
 * subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not claim that you wrote the original software. If you use this software in a product, an acknowledgment in the product documentation would be appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 *
 */

package org.siprop.opencv;

import android.graphics.Rect;

/**
 * A class JNI for interacting with the JNI interface from Java.
 */
public class OpenCV {
    static {
        System.loadLibrary("opencv");
    }

    public native byte[] findContours(int[] data, int w, int h);

    public native boolean createSocketCapture(String address, String port, int width, int height);

    public native void releaseSocketCapture();

    public native boolean grabSourceImageFromCapture();

    public native byte[] getSourceImage();

    public native boolean setSourceImage(int[] data, int w, int h);
    public native boolean setSampleImage(int[] data, int w, int h);

    public native boolean initFaceDetection(String cascadePath);

    public native void releaseFaceDetection();

    public native boolean highlightFaces();

    public native Rect[] findAllFaces();

    public native Rect findSingleFace();
    
    /** Ezysnap Ports **/
    
    public native byte[] smooth();
    
    public native byte[] erode();
    
    public native byte[] dilate();
    
    public native byte[] morphologyexOpen();
    
    public native byte[] morphologyexClose();
    
    public native byte[] morphologyexGradient();
    
    public native byte[] morphologyexTopHat();

    public native byte[] morphologyexBlackHat();
    
    public native byte[] imageROI(int x, int y, int width, int height, int scalar);
    
    public native byte[] drawLine(int x, int y, int x2, int y2);
    
    public native byte[] drawRectangle(int x, int y, int x2, int y2);
    
    public native byte[] drawCircle(int cx, int cy, int radius);
    
    public native byte[] pyramidSegmentation();
    
    public native byte[] threshold(double threshold, double maxValue);
    
    public native byte[] adaptiveThreshold(double maxValue);
    
    public native byte[] sobel(int xorder, int yorder);
    
    public native byte[] laplace();
    
    public native byte[] histogram();
    
    public native byte[] findObject(int width, int height);
}
