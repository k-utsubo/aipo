/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.aimluck.eip.fileio;

import java.io.File;

import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;

import com.aimluck.eip.common.ALCsvAbstractUploadFormData;
import com.aimluck.eip.common.ALCsvTokenizer;
import com.aimluck.eip.common.ALDBErrorException;
import com.aimluck.eip.common.ALPageNotFoundException;
import com.aimluck.eip.fileio.util.FileIOAddressBookCsvUtils;
import com.aimluck.eip.fileio.util.FileIOCsvUtils;
import com.aimluck.eip.modules.actions.common.ALAction;

public class FileIOAddressBookCsvUploadFormData extends
    ALCsvAbstractUploadFormData {

  /** logger */
  @SuppressWarnings("unused")
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(FileIOAddressBookCsvUploadFormData.class.getName());

  /** 一時フォルダ名(番号のみ) */
  private String temp_folder;

  /** 一時ファイルのフルパス */
  protected String temp_file_path;

  /**
   * 初期化 <BR>
   */
  @Override
  public void init(ALAction action, RunData rundata, Context context)
      throws ALPageNotFoundException, ALDBErrorException {
    super.init(action, rundata, context);

    initTempFileName();
  }

  /**
   * 一時フォルダ生成 <BR>
   */
  private void initTempFileName() {
    File tmpfolderRootFolder =
      new File(ALCsvTokenizer.CSV_TEMP_FOLDER
        + File.separator
        + FileIOAddressBookCsvUtils.CSV_ADDRESSBOOK_TEMP_FOLDER);
    if (!tmpfolderRootFolder.exists()) {
      tmpfolderRootFolder.mkdirs();
    }

    temp_folder =
      FileIOCsvUtils.getNewAttachmentFolderName(tmpfolderRootFolder);

    String newfolderpath = tmpfolderRootFolder + File.separator + temp_folder;
    File newfolder = new File(newfolderpath);
    if (!newfolder.exists()) {
      newfolder.mkdirs();
    }

    temp_file_path =
      newfolderpath
        + File.separator
        + FileIOAddressBookCsvUtils.CSV_ADDRESSBOOK_TEMP_FILENAME;
  }

  /**
   * 一時ファイルのフルパスを取得します <BR>
   */
  @Override
  public String getTempFilePath() {
    return temp_file_path;
  }

  /**
   * 一時フォルダ名（番号）を取得します <BR>
   * 
   * @return
   */
  public String getTempFolderIndex() {
    return temp_folder;
  }

}
