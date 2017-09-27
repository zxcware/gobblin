package org.apache.gobblin.example;

import org.apache.gobblin.data.management.copy.CopyConfiguration;
import org.apache.gobblin.data.management.copy.CopyEntity;
import org.apache.gobblin.data.management.copy.CopyableDatasetBase;
import org.apache.gobblin.data.management.copy.CopyableDatasetMetadata;
import org.apache.gobblin.runtime.embedded.EmbeddedGobblin;
import org.apache.gobblin.runtime.embedded.EmbeddedGobblinDistcp;
import org.apache.hadoop.fs.Path;


public class DistcpJobExecutor {
  public void testDistcp() {
    CopyableDatasetBase copyable = new CopyableDatasetBase() {
      @Override
      public String datasetURN() {
        return "dir";
      }
    };

    CopyableDatasetMetadata metadata = new CopyableDatasetMetadata(copyable);
    CopyEntity.DatasetAndPartition dp = new CopyEntity.DatasetAndPartition(metadata, "dir");
    System.out.print(dp.toString());
  }

  public static void main(String[] args) {
    String fromLoc = "/Users/zhchen/Projects/gobblin-jobs/distcp/from/*";
    String toLoc = "/Users/zhchen/Projects/gobblin-jobs/distcp/to";
    try {
      EmbeddedGobblin gobblin = new EmbeddedGobblinDistcp(new Path(fromLoc), new Path(toLoc));
      gobblin.setConfiguration("gobblin.dataset.blacklist", ".DS_Store");
      gobblin.setConfiguration(CopyConfiguration.INCLUDE_EMPTY_DIRECTORIES, "true");
      gobblin.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
