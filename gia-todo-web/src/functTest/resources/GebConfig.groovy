import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver

waiting {
    timeout = 2
}

environments {
    chrome {
        def options = new ChromeOptions()
        options.addArguments("--remote-allow-origins=*")
        driver = { new ChromeDriver(options) }
    }
    firefox {
        driver = { new FirefoxDriver() }
    }
}