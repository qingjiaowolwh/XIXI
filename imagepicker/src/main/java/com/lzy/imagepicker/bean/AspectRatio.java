/*
 * Copyright (C) 2016 The Android Open Source Project
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

package com.lzy.imagepicker.bean;

import android.hardware.Camera;
import android.support.annotation.NonNull;

/**
 * Immutable class for describing proportional relationship between width and height.
 */
public class AspectRatio implements Comparable<AspectRatio>{

    private final int width;
    private final int height;


    public AspectRatio(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean matches(Camera.Size size) {
        int gcd = gcd(size.width, size.height);
        int x = size.width / gcd;
        int y = size.height / gcd;
        return width == x && height == y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AspectRatio that = (AspectRatio) o;

        if (width != that.width) return false;
        return height == that.height;

    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return width + ":" + height;
    }

    public float toFloat() {
        return (float) width / height;
    }


    @Override
    public int compareTo(@NonNull AspectRatio another) {
        if (equals(another)) {
            return 0;
        } else if (toFloat() - another.toFloat() < 0) {
            return 1;
        }
        return -1;
    }


    private static int gcd(int a, int b) {
        while (b != 0) {
            int c = b;
            b = a % b;
            a = c;
        }
        return a;
    }

}
