package com.kakarote.km.service;

import com.kakarote.core.servlet.BaseService;
import com.kakarote.km.entity.BO.MoveFolderBO;
import com.kakarote.km.entity.PO.KmFolder;
import com.kakarote.km.entity.VO.FolderDetailVO;
import com.kakarote.km.entity.VO.TreeListVO;

/**
 * <p>
 * 知识库文件夹 服务类
 * </p>
 *
 * @author wyq
 * @since 2020-05-25
 */
public interface IKmFolderService extends BaseService<KmFolder> {
    public void add(KmFolder kmFolder);

    public void set(KmFolder kmFolder);

    public void delete(Long folderId);

    public void completelyDelete(Long folderId);

    public TreeListVO queryTreeList(Long libraryId);

    public void move(MoveFolderBO moveFolderBO);

    public FolderDetailVO queryById(Long folderId);
}
