package util;


import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.Attachment;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;

import java.io.File;
import java.net.URI;
import java.util.List;

public class ExchangeEmailUtil {

    public static int receive(String serverUrl, String userName, String domain, String pwd, String path, int max) throws Exception {
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
        ExchangeCredentials credentials = new WebCredentials(userName, pwd, domain);
        service.setCredentials(credentials);
        service.setUrl(new URI(serverUrl));
        Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
        //获取邮箱文件数量
        int count = inbox.getTotalCount();
        if (max > 0) count = count > max ? max : count;
        //循环获取邮箱邮件
        ItemView view = new ItemView(count);
        FindItemsResults<Item> findResults = service.findItems(inbox.getId(), view);
        for (Item item : findResults.getItems()) {
            EmailMessage message = EmailMessage.bind(service, item.getId());
            List<Attachment> attachs = message.getAttachments().getItems();
            try {
                if (message.getHasAttachments()) {
                    for (Attachment f : attachs) {
                        if (f instanceof FileAttachment) {
                            //接收邮件到临时目录
                            File tempZip = new File(path, f.getName());
                            ((FileAttachment) f).load(tempZip.getPath());
                        }
                    }
                    //删除邮件
                    //message.delete(DeleteMode.HardDelete);
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        return count;
    }

    public static void main(String[] args) throws Exception {
        receive("https://imail.jd.com/EWS/Exchange.asmx", "", "360buyad.local", "", "tmp", 10);
    }

}
