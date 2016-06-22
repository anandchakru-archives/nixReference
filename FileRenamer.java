import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileRenamer {
	public static void main(String[] args) {
		String className = FileRenamer.class.getName();
		if(args.length!=2){
			System.out.println("Usage: \njava " + className + " \".jpg\" \"_M.jpg\"");
			System.exit(1);
		}
		Path path = Paths.get("");
		if(path!=null){
			String currentPath = path.toAbsolutePath().toString();
			File dir = new File(currentPath);
			if(dir.isDirectory()){
				File[] listFiles = dir.listFiles();
				for(File f: listFiles){
					String oldName = f.getAbsolutePath();
					String newName = oldName.replace(args[0], args[1]);
					if(newName.indexOf(className)>0 || oldName.indexOf(className)>0){
						System.out.println("\n\t\t-~" + newName + "~-\n");
					}
					if(f.isFile() && !oldName.equals(newName)){
						f.renameTo(new File(newName));
					}else{
						System.out.println("skipping:" + oldName);
					}
				}
			}else{
				System.out.println("invalid dir: " + dir.getAbsolutePath());
			}
		}else{
			System.out.println("Unable to find current dir");
		}
	}
}
