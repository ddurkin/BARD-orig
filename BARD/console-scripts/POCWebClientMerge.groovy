
/**
 * Created with IntelliJ IDEA.
 * User: ddurkin
 * Date: 8/15/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
AntBuilder ant = new AntBuilder()

def dstRootDir = '/Users/ddurkin/dev/bard/BARD-df/BARD'
def srcRootDir = '/Users/ddurkin/dev/bard/bard-web-query/bard-web-client'

ant.sequential{
     copyDir(srcRootDir, dstRootDir, 'grails-app', ['conf/**/*', 'i18n/**/*'], ant)
     copyDir(srcRootDir, dstRootDir, 'src', [], ant)
     copyDir(srcRootDir, dstRootDir, 'test', [], ant)
     copyDir(srcRootDir, dstRootDir, 'web-app', [], ant)
}

def copyDir(String srcRootDir, String dstRootDir, String relativeDir,List excludeNames, AntBuilder ant) {
    ant.copy(todir: "${dstRootDir}/${relativeDir}") {
        fileset(dir: "${srcRootDir}/${relativeDir}") {
            excludeNames.each{
                exclude(name: it)
            }
        }
    }
}
