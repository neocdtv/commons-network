package io.neocdtv.commons.network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * NetworkUtil.
 *
 * @author xix
 * @since 30.03.18
 */
public class NetworkUtil {

  public List<InetAddress> findIpv4AddressForActiveInterfaces() throws SocketException {
    final List<InetAddress> addresses = new ArrayList<>();
    final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
    for (NetworkInterface networkInterface : Collections.list(networkInterfaces)) {
      if (networkInterface.isUp()) {
        networkInterface.getInterfaceAddresses().stream().forEach(interfaceAddress -> {
          final InetAddress address = interfaceAddress.getAddress();
          if (address instanceof Inet4Address) {
            addresses.add(address);
          }
        });
      }
    }
    return addresses;
  }

  public Inet4Address getIpv4AddressForInterface(final String interfaceName) throws SocketException {
    NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
    if (networkInterface.isUp()) {
      for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
        InetAddress address = interfaceAddress.getAddress();
        if (address instanceof Inet4Address) {
          return (Inet4Address) address;
        }
      }
    }
    throw new RuntimeException("Unable to access IPv4 address for interface named: " + interfaceName);
  }
}

