/*
 * Copyright (c) 2016, 2025, Oracle and/or its affiliates.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License, version 2.0, as published by
 * the Free Software Foundation.
 *
 * This program is designed to work with certain software that is licensed under separate terms, as designated in a particular file or component or in
 * included license documentation. The authors of MySQL hereby grant you an additional permission to link the program and your derivative works with the
 * separately licensed software that they have either included with the program or referenced in the documentation.
 *
 * Without limiting anything contained in the foregoing, this file, which is part of MySQL Connector/J, is also subject to the Universal FOSS Exception,
 * version 1.0, a copy of which can be found at http://oss.oracle.com/licenses/universal-foss-exception.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License, version 2.0, for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */

package com.mysql.cj.protocol;

import java.io.IOException;

import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.exceptions.ExceptionFactory;

public interface ProtocolEntityReader<T extends ProtocolEntity, M extends Message> {

    /**
     *
     * @param sf
     *            {@link ProtocolEntityFactory} instance
     * @return {@link ProtocolEntity} instance
     * @throws IOException
     *             if an error occurs
     */
    default T read(ProtocolEntityFactory<T, M> sf) throws IOException {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
    }

    /**
     * Reads one result set off of the wire, if the result is actually an
     * update count, creates an update-count only result set.
     *
     * @param maxRows
     *            the maximum number of rows to read (-1 means all rows)
     * @param streamResults
     *            should the driver leave the results on the wire,
     *            and read them only when needed?
     * @param resultPacket
     *            the first packet of information in the result set
     * @param metadata
     *            use this metadata instead of the one provided on wire
     * @param protocolEntityFactory
     *            {@link ProtocolEntityFactory} instance
     *
     * @return a result set that either represents the rows, or an update count
     *
     * @throws IOException
     *             if an error occurs while reading the rows
     */
    default T read(int maxRows, boolean streamResults, M resultPacket, ColumnDefinition metadata, ProtocolEntityFactory<T, M> protocolEntityFactory)
            throws IOException {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
    }

}
