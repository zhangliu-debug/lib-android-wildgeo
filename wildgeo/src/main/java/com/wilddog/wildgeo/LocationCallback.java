/*
 * Wilddog WildGeo Java Library
 *
 * Copyright © 2014 Wilddog - All Rights Reserved
 * https://www.wilddog.com
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binaryform must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY WILDDOG AS IS AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL WILDDOG BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.wilddog.wildgeo;

import com.wilddog.client.SyncError;

/**
 * Classes implementing this interface can be used to receive the locations stored in WildGeo.
 */
public interface LocationCallback {

    /**
     * This method is called with the current location of the key. location will be null if there is no location
     * stored in WildGeo for the key.
     * @param key The key whose location we are getting
     * @param location The location of the key
     */
    public void onLocationResult(String key, GeoLocation location);

    /**
     * Called if the callback could not be added due to failure on the server or security rules.
     * @param wilddogError The error that occurred
     */
    public void onCancelled(SyncError wilddogError);

}