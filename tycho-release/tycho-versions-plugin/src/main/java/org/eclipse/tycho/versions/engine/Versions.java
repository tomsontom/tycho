/*******************************************************************************
 * Copyright (c) 2011 Sonatype Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sonatype Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.tycho.versions.engine;

import org.osgi.framework.Version;

public class Versions {
    private static final String SUFFIX_QUALIFIER = ".qualifier";

    private static final String SUFFIX_SNAPSHOT = "-SNAPSHOT";

    public static String toCanonicalVersion(String version) {
        if (version == null) {
            return null;
        }

        if (version.endsWith(SUFFIX_SNAPSHOT)) {
            return version.substring(0, version.length() - SUFFIX_SNAPSHOT.length()) + SUFFIX_QUALIFIER;
        }

        return version;
    }

    public static void assertIsOsgiVersion(String version) throws NumberFormatException, IllegalArgumentException,
            NullPointerException {
        new Version(version);
    }

    public static String toMavenVersion(String version) {
        if (version == null) {
            return null;
        }

        if (version.endsWith(SUFFIX_QUALIFIER)) {
            return version.substring(0, version.length() - SUFFIX_QUALIFIER.length()) + SUFFIX_SNAPSHOT;
        }

        return version;
    }

    public static boolean isVersionEquals(String a, String b) {
        return toCanonicalVersion(a).equals(toCanonicalVersion(b));
    }

}
